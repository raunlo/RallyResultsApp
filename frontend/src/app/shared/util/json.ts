//http://cloudmark.github.io/Json-Mapping/ Idea taken from


import 'reflect-metadata'
import {Wrapper} from "../model/wrapper";
import {Page} from "../model/page";
import {RallyStage} from "../../results/model/rally-stage";


const jsonMetadataKey = "jsonProperty";

export interface Deserializer<T> {
  deserialize(value: any, metadata: IJsonMetaData<any>): T;
}

export interface IJsonMetaData<T> {
  name?: string,
  clazz?: { new(): T },
  deserilizer?: { new(): Deserializer<T> }
  isArray?: boolean
}

export function JsonProperty<T>(metadata?: IJsonMetaData<T> | string): any {
  if (metadata instanceof String || typeof metadata === "string") {
    return Reflect.metadata(jsonMetadataKey, {
      name: metadata,
      clazz: undefined,
      deserilizer: undefined,
    });
  } else {
    let metadataObj = <IJsonMetaData<T>>metadata;
    return Reflect.metadata(jsonMetadataKey, {
      name: metadataObj ? metadataObj.name : undefined,
      clazz: metadataObj ? metadataObj.clazz : undefined,
      deserilizer: metadataObj ? metadataObj.deserilizer : undefined,
      isArray: metadataObj ? metadata.isArray : undefined
    });
  }
}

export class PageDeserializer implements Deserializer<Page<any>> {
  deserialize(value: any, metadata: IJsonMetaData<any>): Page<any> {
    if (!value) {
      return undefined;
    }
    const clazz = metadata.clazz;
    return value.map(json => JsonUtils.deserialize(new clazz(), json))
  }

}

export class DateDeserializer implements Deserializer<Date> {
  deserialize(value: any, metadata: IJsonMetaData<any>): Date {
    if (value == null) {
      return null;
    }
    return new Date(value);
  }

}

export class WrapperDeserilzer<T> implements Deserializer<Wrapper<any>> {
  deserialize(value: any, metadata: IJsonMetaData<any>): Wrapper<any> {
    if (!value) return undefined;

    return value.map(json => JsonUtils.deserialize(new metadata.clazz, json))
  }

}

export class RallyStagesArrayDeserializer implements Deserializer<RallyStage[]> {
  deserialize(value: any, metadata: IJsonMetaData<any>): any[] {
    if(!value) return undefined;
    return value.map(json => JsonUtils.deserialize(new metadata.clazz, json))
  }

}

export function getClazz(target: any, propertyKey: string): any {
  return Reflect.getMetadata("design:type", target, propertyKey)
}

export function getJsonProperty<T>(target: any, propertyKey: string): IJsonMetaData<T> {
  return Reflect.getMetadata(jsonMetadataKey, target, propertyKey);
}

export default class JsonUtils {
  static isPrimitive(obj) {
    switch (typeof obj) {
      case "string":
      case "number":
      case "boolean":
        return true;
    }
    return !!(obj instanceof String || obj === String ||
      obj instanceof Number || obj === Number ||
      obj instanceof Boolean || obj === Boolean);
  }

  static isArray(object) {
    if (object === Array) {
      return true;
    } else if (typeof Array.isArray === "function") {
      return Array.isArray(object);
    } else {
      return (object instanceof Array);
    }
  }

  static isDate(object) {
    if (object === Date) {
      return true;
    } else if (typeof object.getMonth === 'function') {
      return true;
    } else {
      return (object instanceof Date)
    }
  }

  static serialize<T>(obj: T) {
    let json = new Object();
    Object.keys(obj).forEach((key: string) => {
      let propertyMetadataFn: () => any = () => {
        let propertyName = key;
        let value = obj ? obj[propertyName] : undefined;
        let clazz = getClazz(obj, key);
        if (!value) return undefined;
        if (JsonUtils.isArray(clazz)) {
          let metadata = getJsonProperty(obj, key);
          if (metadata.clazz || JsonUtils.isPrimitive(clazz)) {
            if (JsonUtils.isArray(value)) {
              return value.map(
                (item) => JsonUtils.serialize(item)
              );
            } else {
              return undefined;
            }
          } else {
            return value;
          }

        } else if (!JsonUtils.isPrimitive(clazz)) {
          return JsonUtils.serialize(value);
        } else {
          return obj ? obj[propertyName] : undefined;
        }
      };

      let propertyMetadata = getJsonProperty(obj, key);
      if (propertyMetadata) {
        json[propertyMetadata.name] = propertyMetadataFn();
      } else {
        if (obj && obj[key] !== undefined) {
          if (!JsonUtils.isPrimitive(obj[key])) {
            json[key] = JsonUtils.serialize(obj[key]);
          } else {
            json[key] = obj[key];
          }
        }
      }
    });
    return json;
  }

  static deserialize<T>(obj: T, jsonObject): T {
    if ((obj === undefined) || (jsonObject === undefined)) return undefined;
    if(obj instanceof String) return jsonObject;
    Object.keys(obj).forEach((key) => {
      let propertyMetadataFn: (IJsonMetaData) => any = (propertyMetadata) => {
        let propertyName = propertyMetadata.name || key;
        let value = jsonObject ? jsonObject[propertyName] : undefined;
        let clazz = getClazz(obj, key) || propertyMetadata.clazz;
        let metadata = getJsonProperty(obj, key) ? getJsonProperty(obj, key) : getJsonProperty(jsonObject, key);
        if (propertyMetadata.deserilizer !== undefined) {
          const jsonMetadata = getJsonProperty(jsonObject, key);
          return new metadata.deserilizer().deserialize(value, Object.assign({}, metadata, jsonMetadata))
        }
        if (metadata.isArray || JsonUtils.isArray(clazz)) {
          if (JsonUtils.isPrimitive(clazz) || (metadata.clazz)) {
            if (JsonUtils.isArray(value)) {
              return value.map(
                (item) => JsonUtils.deserialize(new metadata.clazz(), item)
              );
            } else {
              return undefined;
            }
          } else {
            return value;
          }
        } else if (!JsonUtils.isPrimitive(clazz)) {
          return JsonUtils.deserialize(new clazz(), value);
        } else {
          return jsonObject ? jsonObject[propertyName] : undefined;
        }
      };

      let propertyMetadata = getJsonProperty(obj, key);
      if (propertyMetadata) {
        obj[key] = propertyMetadataFn(propertyMetadata);
      } else {
        if (jsonObject && jsonObject[key] !== undefined) {
          obj[key] = jsonObject[key];
        }
      }
    });
    return obj;
  }
}
