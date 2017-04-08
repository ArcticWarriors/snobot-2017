/*
 * ISmartDashboardUpdaterModule.h
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#ifndef SRC_SNOBOTLIB_ISMARTDASHBOARDUPDATERMODULE_H_
#define SRC_SNOBOTLIB_ISMARTDASHBOARDUPDATERMODULE_H_


class ISmartDashboardUpdaterModule
{
public:
    virtual ~ISmartDashboardUpdaterModule()
    {

    }

    /**
     * Updates information that is sent to SmartDashboard Takes Enum argument
     */
    virtual void updateSmartDashboard() = 0;
};



#endif /* SRC_SNOBOTLIB_ISMARTDASHBOARDUPDATERMODULE_H_ */
