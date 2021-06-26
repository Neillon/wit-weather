package br.com.neillon.network.exception

import br.com.neillon.network.Constants

class InvalidApiKeyException: Exception(Constants.Network.Exceptions.INVALID_API_KEY_STATUS_MESSAGE)