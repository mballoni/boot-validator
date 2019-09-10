RELEASE=$(date +"%Y%m")
COMMITS=$(git rev-list --count HEAD --since=$(date +"%Y%m")-01)

echo ${RELEASE}-${COMMITS}