# Read from the file file.txt and output all valid phone numbers to stdout.

# Details
# start with:
#(0-9){3}
#or
#\([0-9]{3}\)
#be aware the last space of the above condition
# since we have two conditions at start with part, so use the () to mark:
#^([0-9]{3}-|\([0-9]{3}\) )
# next is normal case and end with
# the /.../ is used to mark this string is regexp in awk

awk '/^([0-9]{3}-|\([0-9]{3}\) )[0-9]{3}-[0-9]{4}$/' file.txt