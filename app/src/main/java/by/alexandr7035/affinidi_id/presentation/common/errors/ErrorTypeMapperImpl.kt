package by.alexandr7035.affinidi_id.presentation.common.errors

import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.presentation.helpers.resources.ResourceProvider
import javax.inject.Inject

class ErrorTypeMapperImpl @Inject constructor(private val resourceProvider: ResourceProvider): ErrorTypeMapper {
    override fun map(errorType: ErrorType): DetailedErrorUi {

        return when (errorType) {
            ErrorType.FAILED_CONNECTION -> {
                DetailedErrorUi(
                    errorType,
                    resourceProvider.getString(R.string.error_failed_connection_title),
                    resourceProvider.getString(R.string.error_failed_connection)
                )
            }
            // All not implemented errortypes will be shown as unknown
            else -> {
                DetailedErrorUi(
                    errorType,
                    resourceProvider.getString(R.string.error_unknown_title),
                    resourceProvider.getString(R.string.error_unknown)
                )
            }
        }
    }
}