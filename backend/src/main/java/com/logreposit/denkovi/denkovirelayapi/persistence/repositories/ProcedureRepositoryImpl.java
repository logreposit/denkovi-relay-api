package com.logreposit.denkovi.denkovirelayapi.persistence.repositories;

import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.Procedure;
import java.util.Optional;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.WriteResult;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcedureRepositoryImpl implements ProcedureRepository
{
    private static final Logger logger = LoggerFactory.getLogger(ProcedureRepositoryImpl.class);

    private final ObjectRepository<Procedure> repository;

    @Autowired
    public ProcedureRepositoryImpl(Nitrite database)
    {
        this.repository = database.getRepository(Procedure.class);
    }

    @Override
    public Optional<Procedure> get(String id)
    {
        Cursor<Procedure> cursor = this.repository.find(ObjectFilters.eq("id", id));
        Procedure procedure = cursor.firstOrDefault();

        return Optional.ofNullable(procedure);
    }

    @Override
    public Procedure save(Procedure procedure)
    {
        Optional<Procedure> storedProcedure = this.get(procedure.getId());

        if (!storedProcedure.isPresent())
        {
            this.insert(procedure);
        }
        else
        {
            this.update(procedure);
        }

        return procedure;
    }

    private void insert(Procedure procedure)
    {
        WriteResult writeResult = this.repository.insert(procedure);

        if (writeResult.getAffectedCount() != 1)
        {
            logger.error("Unable to insert Procedure object");

            throw new RuntimeException("unbale to insert Procedure object");
        }
    }

    private void update(Procedure procedure)
    {
        WriteResult writeResult = this.repository.update(ObjectFilters.eq("id", procedure.getId()), procedure);

        if (writeResult.getAffectedCount() != 1)
        {
            logger.error("Unable to update Procedure object");

            throw new RuntimeException("unbale to update Procedure object");
        }
    }
}
