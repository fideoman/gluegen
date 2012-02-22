#! /bin/sh

SDIR=`dirname $0` 

if [ -e $SDIR/setenv-build-jogl-x86_64.sh ] ; then
    . $SDIR/setenv-build-jogl-x86_64.sh
fi

PATH=`pwd`/lib/linux/arm-linux-gnueabi/bin:$PATH
export PATH

#    -Dc.compiler.debug=true 
#    -Dgluegen.cpptasks.detected.os=true \
#    -DisUnix=true \
#    -DisLinux=true \
#    -DisLinuxARMv7=true \
#    -DisX11=false \

export HOST_UID=jogamp
export HOST_IP=jogamp02
export HOST_RSYNC_ROOT=PROJECTS/JOGL

export TARGET_UID=jogamp
export TARGET_IP=panda01
export TARGET_ROOT=/home/jogamp/projects-cross
export TARGET_ANT_HOME=/usr/share/ant

export TARGET_PLATFORM_LIBS=/opt-linux-armv7-eabi/lib
export TARGET_JAVA_LIBS=/opt-linux-armv7-eabi/jre/lib/arm

ant \
    -Drootrel.build=build-linux-armv7 \
    -Dgluegen.cpptasks.detected.os=true \
    -DisUnix=true \
    -DisLinux=true \
    -DisLinuxARMv7=true \
    -DisX11=true \
    -DisCrosscompilation=true \
    \
    -DuseKD=true \
    -DuseOpenMAX=true \
    -DuseBroadcomEGL=true \
    $* 2>&1 | tee make.gluegen.all.linux-armv7-cross.log


