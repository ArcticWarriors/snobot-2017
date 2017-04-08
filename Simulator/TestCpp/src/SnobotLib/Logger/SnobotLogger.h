/*
 * SnobotLogger.h
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#ifndef SRC_SNOBOTLIB_LOGGER_SNOBOTLOGGER_H_
#define SRC_SNOBOTLIB_LOGGER_SNOBOTLOGGER_H_

#include "SnobotLib/Logger/ILogger.h"

class SnobotLogger: public ILogger
{
public:
    SnobotLogger();
    virtual ~SnobotLogger();

    void updateLogger(int aValue) override;

    void updateLogger(double aValue) override;

    void addHeader(const std::string& aHeader) override;
};

#endif /* SRC_SNOBOTLIB_LOGGER_SNOBOTLOGGER_H_ */
