//const environment = process.env.NODE_ENV;
export const FETCH_CHANGEASSESSMENTPAGES_SUCCESS = 'FETCH_CHANGEASSESSMENTPAGES_SUCCESS';
export const FETCH_CHANGEASSESSMENTPAGES_FAILURE = 'FETCH_CHANGEASSESSMENTPAGES_FAILURE';

export const fetchChangeAssessmentPagesSuccess = (result) => ({
    type: FETCH_CHANGEASSESSMENTPAGES_SUCCESS,
    payload: result,
});
export const fetchChangeAssessmentPagesFailure = (error) => ({
    type: FETCH_CHANGEASSESSMENTPAGES_FAILURE,
    payload: error,
});
export default function getChangeAssessmentPages(result) {
    return function (dispatch) {
        dispatch(fetchChangeAssessmentPagesSuccess(result));
    }
}