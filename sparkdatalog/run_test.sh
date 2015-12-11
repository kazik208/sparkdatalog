#!/bin/bash
set -eu

# initialize vars
class=""
file=""

print_help() {
	cat <<-EOF
	usage: $0 [class name] [data file]
	
	Prepares environment to run test from a given class.

	[data file] is an optional argument, and if supplied sets
	data file used in a specified class.
	EOF
}

# set class variable to java class name with package
set_class() {
	local class_name="$1"
	# find file with a given name
	file="$(find . -name "*${class_name}*")"
	if ! [ "$(echo "$file" | wc -l)" -eq 1 ]; then
		echo "[ERROR] 0 or multiple files $class_name found"
		echo "$file"
		exit 1
	fi
	# remove preceding path
	class=${file#*scala/}
	# replace / with . to get java classname
	class=${class//[\/]/.}
}

# replaces mainClass in the build file with a given argument
replace_build() {
	local arg="$1"
	sed -i -e "/^mainClass/ s/\".*\"/\"$arg\"/g" build.sbt
	echo "class $arg set as main class"
}

# replace the data file in the given class file
# file is searched for [root + ".*"]
replace_data() {
	local arg="$1"
	if ! [ -f "$arg" ]; then
		echo "unable to replace data, file $arg not found"
		exit 1
	fi
	sed -i -e "/root + \".*\"/ s/\".*\"/\"$arg\"/g" "$file"
	echo "file $arg set as data input in class $class"
}

if [ $# == 0 ]; then
	print_help
	exit 0
fi

set_class "$1"
replace_build "$class"
if [ $# == 2 ]; then
	replace_data "$2"
fi

exit 0
