#!/bin/bash
set -eu

if [ $# -eq 0 ] || [ "$1" == "-h" ]; then
	cat <<-EOF
	usage: $0
	Prints to stdout a list of edges for a cycle containig N+1 elements.
	EOF
	exit 1
fi

awk -v n="$1" '
	END{ 
		while(i < n) {
			printf("%d %d\n", i, ++i);
		}
		printf("%d 0\n", i);
	}' < /dev/null
		
