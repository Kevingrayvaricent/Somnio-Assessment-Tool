//const environment = process.env.NODE_ENV;
export const FETCH_REMOVE_AND_SHOW_RESULTS_SUCCESS = 'FETCH_REMOVE_AND_SHOW_RESULTS_SUCCESS';
export const FETCH_REMOVE_AND_SHOW_RESULTS_FAILURE = 'FETCH_REMOVE_AND_SHOW_RESULTS_FAILURE';

export const fetchRemoveAndShowResultsSuccess = (show) => ({
    type: FETCH_REMOVE_AND_SHOW_RESULTS_SUCCESS,
    payload: show,
});
export const fetchRemoveAndShowResultsFailure = (error) => ({
    type: FETCH_REMOVE_AND_SHOW_RESULTS_FAILURE,
    payload: error,
});
export default function getRemoveAndShowResultsComponents(show) {
    console.log('results ' + show);
    return function (dispatch) {
        dispatch(fetchRemoveAndShowResultsSuccess(show));
    }
}