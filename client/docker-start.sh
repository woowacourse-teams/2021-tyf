# should pass argument for tag
# should exist ssl cert/key file on /etc/ssl

sudo docker rm  -f tyf-client

if [ -z "$1" ]; then
  sudo docker run --name tyf-client -p 443:443 -p 80:80 -v /etc/ssl:/etc/ssl jho2301/tyf-client
else
  sudo docker run --name tyf-client -p 443:443 -p 80:80 -v /etc/ssl:/etc/ssl jho2301/tyf-client:$1
fi
