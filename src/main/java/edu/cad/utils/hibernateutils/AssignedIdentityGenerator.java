package edu.cad.utils.hibernateutils;

import edu.cad.entities.interfaces.IDatabaseEntity;
import java.io.Serializable;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IncrementGenerator;

public class AssignedIdentityGenerator extends IncrementGenerator {
 
    @Override
    public Serializable generate(SessionImplementor session, Object obj) {
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
