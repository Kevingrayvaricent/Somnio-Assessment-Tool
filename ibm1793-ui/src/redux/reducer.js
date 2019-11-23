import { combineReducers } from 'redux';
import removeAndShowContent from './reducers/removeAndShowContentReducer';
import removeAndShowResults from './reducers/removeAndShowResultsReducer';
import assessment from './reducers/assessmentReducer';
import changeAssessmentPages from './reducers/changeAssessmentPagesReducer';

const rootReducer = combineReducers({
    removeAndShowContent,
    removeAndShowResults,
    assessment,
    changeAssessmentPages

});
export default rootReducer;