package org.aura.services.interfaces;

import org.aura.models.workforce;

public interface workForceInterfaceServ {
    workforce getWorkForce(int id);
    void createWorkForce(workforce workforce);
}
