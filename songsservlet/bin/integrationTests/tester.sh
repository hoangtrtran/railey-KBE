#! /bin/sh

#
# Usage:
# ./tester.sh TEAMNAME 
# Example:
# ./tester.sh teames
# 

if [ "$#" -ne 1 ]; then
    echo "Illegal number of parameters"
    echo "Usage: ./tester.sh TEAMNAME"
    echo "Example: ./tester.sh teames"
    exit 1
fi

echo "--- REQUESTING SONG NUMBER 6 IN XML --------"
curl -X GET \
     -H "Accept: application/xml" \
     -v "http://localhost:8080/songsservlet-railey/songs?songId=6"
echo "---"

echo "--- REQUESTING SONG NUMBER 6 IN JSON --------"
curl -X GET \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsservlet-railey/songs?songId=6"
echo "---"

echo "--- REQUESTING SONG NUMBER 6 IN JSON --------"
curl -X GET \
     -v "http://localhost:8080/songsservlet-railey/songs?songId=6"
echo "---"

echo "--- REQUESTING SONG NUMBER 6 IN JSON --------"
curl -X GET \
    -H "Accept: */*" \
     -v "http://localhost:8080/songsservlet-railey/songs?songId=6"
echo "---"

echo "--- REQUESTING SONG NUMBER 6 IN JSON --------"
curl -X GET \
    -H "Accept: plain/text" \
     -v "http://localhost:8080/songsservlet-railey/songs?songId=6"
echo "---"

echo "--- PUTTING SONG 6 IN JSON ------------------"
curl -X PUT \
     -H "Content-Type: application/json" \
     -d "@einSong.json" \
     -v "http://localhost:8080/songsservlet-railey/songs?songId=6"
echo "---"

echo "--- PUTTING SONG 6 IN JSON ------------------"
curl -X PUT \
     -d "@einSong.json" \
     -v "http://localhost:8080/songsservlet-railey/songs?songId=6"
echo "---"

#echo "--- SHUTTING DOWN TOMCAT -----------"
#$CATALINA_HOME/bin/shutdown.sh
