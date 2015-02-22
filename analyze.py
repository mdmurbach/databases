import psycopg2


conn_string = "dbname='BookData' user='mdmurbach' host='localhost' password='1423Ballard'"

print "Connecting to database   -> %s" % (conn_string)

conn = psycopg2.connect(conn_string)

cursor = conn.cursor()
print "Connected"

sqlCode = "INSERT INTO Words(top_word,second_word,bookID) VALUES ('the','a',1);"

cursor.execute(sqlCode)

cursor.execute("SELECT * FROM Words")

records = cursor.fetchall()

print  records
print "Done"
