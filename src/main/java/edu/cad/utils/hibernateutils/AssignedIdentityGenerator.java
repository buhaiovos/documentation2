package edu.cad.utils.hibernateutils;

import edu.cad.entities.interfaces.IDatabaseEntity;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IncrementGenerator;

import java.io.Serializable;

public class AssignedIdentityGenerator extends IncrementGenerator {
 
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) {
        if(obj instanceof IDatabaseEntity) {
            IDatabaseEntity entity = (IDatabaseEntity) obj;
            int id = entity.getId();
            if(id > 0) {
                return id;
            }
        }

        return super.generate(session, obj);
    }
}
