*** Pokretanje aplikacije u localhost-u ***
dev_appserver.py --host=0.0.0.0 <PUTANJA_DO_FOLDERA_GAE_ENDPOINTS_PROJEKTA>

*** Generisanje biblioteka za pristup servisu na osnovu Python source-a ***
endpointscfg.py get_client_lib java services.ReviewerSyncApi
endpointscfg.py get_client_lib java services.ReviewerCRUDApi

Explorer u produkciji
https://elevated_surge_702.appspot.com/_ah/api/explorer