copy (
SELECT datahora||'-03',ascii(ordem)||right(ordem,-1), longitude, latitude
from fe_gps 
where (latitude between -24.0 and -21.0) and (longitude between -45.0 and -42.0) limit 1500000
)
to '/home/raphaeldumas/git/traffic-engine-app/fe_data/fe_sample.csv'
with (format 'csv');
