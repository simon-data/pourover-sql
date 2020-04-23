package com.simondata.pouroversql.util;

import com.jcraft.jsch.*;
import java.time.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simondata.pouroversql.ExtractorRunner;

public class ProgressMonitor implements SftpProgressMonitor{
    private final Logger logger = LoggerFactory.getLogger(ExtractorRunner.class);
    private long max                = 0;
    private long count              = 0;
    private long percent            = 0;
    // private CallbackContext callbacks = null;
    
    // If you need send something to the constructor, change this method
    public ProgressMonitor() {}

    public void init(int op, java.lang.String src, java.lang.String dest, long max) {
        this.max = max;
        LocalTime time = LocalTime.now();
        logger.info("starting at " + time + ": 0 of " + max);
    }

    public boolean count(long bytes){
        this.count += bytes;
        LocalTime time = LocalTime.now();
        long percentNow = this.count*100/max;
        if(percentNow>this.percent){
            this.percent = percentNow;

            logger.info(time + "progress: " + this.percent + "%"); // Progress 0,0
        }
        return(true);
    }

    public void end(){
        LocalTime time = LocalTime.now();
        logger.info("finished download at " + time);// The process is over
    }
}