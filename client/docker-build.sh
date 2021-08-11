# should pass argument as tag (semver)

if [ -z "$1" ]; then
  echo "error: should pass argument as tag (semver)"
  exit 2;
fi

sudo docker build -t jho2301/tyf-client:$1 .
sudo docker build -t jho2301/tyf-client .
