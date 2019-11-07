#!/bin/bash

gunzip adx-edge.2019-10-10-*.gz

cat adx-edge.2019-10-10*.log | grep 'dspAppId: 355' >> wangxiang.log

cat wangxiang.log | grep '"price"' | cut -d ':' -f 31 | cut -c 1-7 | sed 's/,[.]*//' | sed 's/"[a-z]*//' >> wangxiangPrice.log
#cat wangxiang.log | grep '"price"' | cut -d ':' -f 31 | cut -c 1-7 | sed 's/,[.]*//' | awk '{sum+=$1} END {print sum}'
rm wangxiang*.log
