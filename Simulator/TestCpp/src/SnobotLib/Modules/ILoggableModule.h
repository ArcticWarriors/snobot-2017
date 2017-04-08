/*
 * ILoggableModule.h
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#ifndef SRC_SNOBOTLIB_ILOGGABLEMODULE_H_
#define SRC_SNOBOTLIB_ILOGGABLEMODULE_H_


class ILoggableModule
{
public:
    virtual ~ILoggableModule()
    {

    }

    /**
     * Perform initialization.
     */
    virtual void initializeLogHeaders() = 0;

    /**
     * Updates the logger.
     */
    virtual void updateLog() = 0;
};



#endif /* SRC_SNOBOTLIB_ILOGGABLEMODULE_H_ */
