# should pass argument as tag (semver)

if [ -z "$1" ]; then
  echo "error: should pass argument as tag (semver)"
  exit 2;
fi

# specified semver tag
sudo docker push jho2301/tyf-client:$1

# push latest
sudo docker push jho2301/tyf-client
