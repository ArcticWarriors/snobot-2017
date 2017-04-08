/*
 * ISubsystem.h
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#ifndef SRC_SNOBOTLIB_MODULES_ISUBSYSTEM_H_
#define SRC_SNOBOTLIB_MODULES_ISUBSYSTEM_H_

#include "SnobotLib/Modules/IControllableModule.h"
#include "SnobotLib/Modules/IUpdateableModule.h"
#include "SnobotLib/Modules/ILoggableModule.h"
#include "SnobotLib/Modules/ISmartDashboardUpdaterModule.h"

class ISubsystem: public IControllableModule, public IUpdateableModule, public ILoggableModule, public ISmartDashboardUpdaterModule
{
public:
    virtual ~ISubsystem()
    {
    }
};




#endif /* SRC_SNOBOTLIB_MODULES_ISUBSYSTEM_H_ */
