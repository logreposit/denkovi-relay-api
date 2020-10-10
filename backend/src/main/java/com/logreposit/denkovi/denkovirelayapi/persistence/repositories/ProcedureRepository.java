package com.logreposit.denkovi.denkovirelayapi.persistence.repositories;

import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.Procedure;
import java.util.List;
import java.util.Optional;

public interface ProcedureRepository
{
    Optional<Procedure> get(String id);
    Procedure save(Procedure procedure);
    void delete(String id);
    List<Procedure> findAll();
}
