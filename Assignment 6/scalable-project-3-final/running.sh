#!/bin/bash
echo hello world!
(java oldmain ire localhost 33333 239.255.255.1 239.255.255.0 33333 0)&
(java oldmain fra localhost 33333 239.255.255.2 239.255.255.1 33333 ire)&
(java oldmain eng localhost 33333 239.255.255.6 239.255.255.0 33333 0)&
(java oldmain ger localhost 33333 239.255.255.7 239.255.255.6 33333 eng)&
(java oldmain spa localhost 33333 239.255.255.8 239.255.255.6 33333 eng)&
(java oldmain tur localhost 33333 239.255.255.3 239.255.255.1 33333 ire)&
(java oldmain chi localhost 33333 239.255.255.4 239.255.255.3 33333 tur)&
(java oldmain qat localhost 33333 239.255.255.9 239.255.255.8 33333 spa)&
(java oldmain ind localhost 33333 239.255.255.10 239.255.255.9 33333 qat)&
(java oldmain Jap localhost 33333 239.255.255.5 239.255.255.4 33333 chi)&

