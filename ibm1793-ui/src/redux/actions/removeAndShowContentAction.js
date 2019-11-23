//const environment = process.env.NODE_ENV;
export const FETCH_REMOVE_AND_SHOW_SUCCESS = 'FETCH_REMOVE_AND_SHOW_SUCCESS';
export const FETCH_REMOVE_AND_SHOW_FAILURE = 'FETCH_REMOVE_AND_SHOW_FAILURE';
export const fetchRemoveAndShowSuccess = (show) => ({
    type: FETCH_REMOVE_AND_SHOW_SUCCESS,
    payload: show,
});
export const fetchRemoveAndShowFailure = (error) => ({
    type: FETCH_REMOVE_AND_SHOW_FAILURE,
    payload: error,
});
export default function getRemoveAndShowComponents(show) {
    console.log(show);
    return function (dispatch) {
        dispatch(fetchRemoveAndShowSuccess(show));
    }
}