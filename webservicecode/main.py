import endpoints
from services import ReviewerCRUDApi,ReviewerSyncApi

package = 'Hello'

application = endpoints.api_server([ReviewerCRUDApi, ReviewerSyncApi])