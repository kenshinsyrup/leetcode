# Read from the file file.txt and output the tenth line to stdout.

# Details:
# https://www.thegeekstuff.com/2010/01/8-powerful-awk-built-in-variables-fs-ofs-rs-ors-nr-nf-filename-fnr/?ref=binfind.com/web
# NR: Number of Records Variable
# Awk NR gives you the total number of records being processed or line number. In the following awk NR example, NR variable has line number, in the END section awk NR tells you the total number of records in a file.

awk 'NR == 10' file.txt