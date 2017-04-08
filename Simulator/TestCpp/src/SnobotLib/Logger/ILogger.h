/*
 * ILogger.h
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#ifndef SRC_ILOGGER_H_
#define SRC_ILOGGER_H_

#include <string>

class ILogger
{
public:

    virtual ~ILogger()
    {

    }

    virtual void updateLogger(int aValue) = 0;

    virtual void updateLogger(double aValue) = 0;

    virtual void addHeader(const std::string& aHeader) = 0;
};



#endif /* SRC_ILOGGER_H_ */
