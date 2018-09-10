import pandas as pd
from bs4 import BeautifulSoup
    
html_string = '''
    <table>
        <tr>
            <td> Hello! </td>
            <td> Table </td>
        </tr>
        <tr>
            <td> Hello! </td>
            <td> Table </td>
        </tr>
    </table>
'''
    
soup = BeautifulSoup(html_string, 'lxml') # Parse the HTML as a string
    
table = soup.find_all('table')[0] # Grab the first table
print(table)    
new_table = pd.DataFrame(columns=range(0,20), index = [0,1,2,3,4,5,6]) # I know the size



for x in range(0, 2):
    row_marker=x
    for row in table.find_all('tr'):
        column_marker = 0
        columns = row.find_all('td')
        for column in columns:
            new_table.iat[row_marker,column_marker] = column.get_text()
            print(column.get_text()) 
            column_marker += 1


print(new_table)
input()
