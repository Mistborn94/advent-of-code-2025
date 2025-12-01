#/bin/bash
# Script to commit the inputs submodule and solution for the day

year=$1
day=$2
package=day$2

cd src/main/resources/inputs
git add $year/$package
git commit -m "$year Day $day"

cd -
git add src/main/resources/inputs
git add src/main/kotlin/helper
git add src/main/kotlin/$package
git add src/test/kotlin/$package
git add src/test/resources/$package
git commit -m "Solve $year Day $day"