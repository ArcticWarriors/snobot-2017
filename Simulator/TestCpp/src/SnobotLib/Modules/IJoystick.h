/*
 * IJoystick.h
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#ifndef SRC_SNOBOTLIB_IJOYSTICK_H_
#define SRC_SNOBOTLIB_IJOYSTICK_H_

#include "SnobotLib/Modules/IUpdateableModule.h"
#include "SnobotLib/Modules/ILoggableModule.h"
#include "SnobotLib/Modules/ISmartDashboardUpdaterModule.h"

class IJoystick: public IUpdateableModule,
        public ILoggableModule,
        public ISmartDashboardUpdaterModule
{
public:
    virtual ~IJoystick()
    {
    }
}
;


#endif /* SRC_SNOBOTLIB_IJOYSTICK_H_ */
