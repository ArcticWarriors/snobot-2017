/*
 * ASnobot.h
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#ifndef SRC_SNOBOTLIB_ASNOBOT_H_
#define SRC_SNOBOTLIB_ASNOBOT_H_

#include "SnobotLib/Modules/IControllableModule.h"
#include "SnobotLib/Modules/IUpdateableModule.h"
#include "SnobotLib/Modules/ILoggableModule.h"
#include "SnobotLib/Modules/ISmartDashboardUpdaterModule.h"
#include <vector>

// WpiLIb
#include "IterativeRobot.h"

class ASnobot: public frc::IterativeRobot
{
public:
    ASnobot();
    virtual ~ASnobot();

    void AutonomousInit() override;

    void AutonomousPeriodic() override;

    void TeleopInit() override;

    void TeleopPeriodic() override;

    void TestPeriodic() override;

protected:

    // Super cheesy way to do "instanceof" equivalent
    template<typename T>
    void addModule(const std::shared_ptr<T>& aModule)
    {
        T* module = aModule.get();

        if (IUpdateableModule* temp = dynamic_cast<IUpdateableModule*>(module))
        {
            mUpdateableModules.push_back(aModule);
        }
        if (IControllableModule* temp = dynamic_cast<IControllableModule*>(module))
        {
            mControllableModules.push_back(aModule);
        }
        if (ILoggableModule* temp = dynamic_cast<ILoggableModule*>(module))
        {
            mLoggableModules.push_back(aModule);
        }
        if (ISmartDashboardUpdaterModule* temp = dynamic_cast<ISmartDashboardUpdaterModule*>(module))
        {
            mSmartDashboardModules.push_back(aModule);
        }
    }

    virtual void update();
    virtual void control();
    virtual void updateLog();
    virtual void updateSmartDashboard();

    std::vector<std::shared_ptr<IUpdateableModule>> mUpdateableModules;
    std::vector<std::shared_ptr<IControllableModule>> mControllableModules;
    std::vector<std::shared_ptr<ILoggableModule>> mLoggableModules;
    std::vector<std::shared_ptr<ISmartDashboardUpdaterModule>> mSmartDashboardModules;
};

#endif /* SRC_SNOBOTLIB_ASNOBOT_H_ */
