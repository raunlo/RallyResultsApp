package com.ralohmus.rallyresults.persistence;

import com.ralohmus.rallyresults.core.domain.Competitor;
import com.ralohmus.rallyresults.core.ports.persistence.CompetitorPersistencePort;
import com.ralohmus.rallyresults.persistence.entities.competitor.CompetitorDbo;
import com.ralohmus.rallyresults.persistence.entities.CompetitorDbo_;
import com.ralohmus.rallyresults.persistence.mapper.CompetitorDboToCompetitorMapper;
import com.ralohmus.rallyresults.persistence.mapper.CompetitorToCompetitorDboMapper;
import com.ralohmus.rallyresults.persistence.repos.CompetitorRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompetitorPersistenceAdapter extends CrudPersistenceAdapter<Competitor, CompetitorDbo> implements CompetitorPersistencePort {

    public CompetitorPersistenceAdapter(CompetitorRepository competitorRepository,
                                        CompetitorDboToCompetitorMapper competitorDboToCompetitorMapper,
                                        CompetitorToCompetitorDboMapper competitorToCompetitorDboMapper) {
        super(competitorRepository, competitorDboToCompetitorMapper, competitorToCompetitorDboMapper);
    }

    @Override
    public void saveCompetitorIfItDoesNotExist(Competitor competitor) {
        var jpaFilter = Specification
                .<CompetitorDbo>where((competitorDbo, cq, cb) -> cb.equal(competitorDbo.get(CompetitorDbo_.FIRST_NAME), competitor.getFirstName()))
                .and((competitorDbo, cq, cb) -> cb.equal(competitorDbo.get(CompetitorDbo_.LAST_NAME), competitor.getLastName()));
        Optional<CompetitorDbo> competitorDbo = repository.findOne(jpaFilter);

        if (competitorDbo.isPresent()) {
            if(!isCompetitorDboImportantFieldsSame(competitorDbo.get(), competitor)) {
                save(competitor);
            }
        } else {
            save(competitor);
        }
    }

    private boolean isCompetitorDboImportantFieldsSame(CompetitorDbo competitorDbo, Competitor competitor) {
        return false;
    }
}
