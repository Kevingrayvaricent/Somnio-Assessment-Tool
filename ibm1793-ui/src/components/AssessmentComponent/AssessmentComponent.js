import React, { Component } from 'react';
import Page from './AssessmentComponentPage';
import { connect } from 'react-redux';
import { withRouter } from 'react-router-dom';
import getRemoveAndShowComponents from '../../redux/actions/removeAndShowContentAction';
import getAssesmentResults from '../../redux/actions/assessmentAction';
import getRemoveAndShowResultsComponents from '../../redux/actions/removeAndShowResultsAction';
import answerStatusObject from '../../states/assessments';
import getChangeAssessmentPages from '../../redux/actions/changeAssessmentPagesAction';


class AssessmentComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      additionalProps: {
        className: 'some-className',
        onSubmit: e => {
          e.preventDefault();
        },
      },
      TextInputPropsQuestion1: {
        className: 'bx--form-item question_1',
        labelText: '',
        id: 'question1',
        placeholder: 'e.g. $1,000,000',
      },
      TextInputPropsQuestion2: {
        className: 'bx--form-item question_2',
        labelText: '',
        id: 'question2',
        placeholder: 'e.g. 4%',
      },
      TextInputPropsQuestion3: {
        className: 'bx--form-item question_3',
        labelText: '',
        id: 'question3',
        placeholder: 'e.g. 20',
      },
      fieldsetRadioProps: {
        className: 'question4',
        id: 'question4',
        legendText: '',
      },
      radioProps: {
        className: 'bx--radio-button__label',
      },
      question4ContentRadioButton: [
        { value: "yes", id: "radio-4-1", labelText: "Yes" },
        { value: "No", id: "radio-4-2", labelText: "No" }
      ],
      question5ContentRadioButton: [
        { value: "Spreadsheets and a manual process", id: "radio-5-1", labelText: "Spreadsheets and a manual process" },
        { value: "A homegrown solution", id: "radio-5-2", labelText: "A homegrown solution" },
        { value: "A CRM solution", id: "radio-5-3", labelText: "A CRM solution" },
        { value: "An SPM vendor", id: "radio-5-4", labelText: "An SPM vendor" }
      ],
      question6ContentRadioButton: [
        { value: "1-5", id: "radio-6-1", labelText: "1-5" },
        { value: "6-9", id: "radio-6-2", labelText: "6-9" },
        { value: "10-14", id: "radio-6-3", labelText: "10-14" },
        { value: ">15", id: "radio-6-4", labelText: ">15" },
      ],
      question7ContentRadioButton: [
        { value: "Monthly", id: "radio-7-1", labelText: "Monthly" },
        { value: "Quarterly", id: "radio-7-2", labelText: "Quarterly" },
        { value: "Annually", id: "radio-7-3", labelText: "Annually" },

      ],
      question8ContentRadioButton: [
        { value: "1-6", id: "radio-8-1", labelText: "1-6" },
        { value: "7-10", id: "radio-8-2", labelText: "7-10" },
        { value: "11-20", id: "radio-8-3", labelText: "11-20" },
        { value: ">20", id: "radio-8-4", labelText: ">20" },
      ],
      showAndHideQuestions: true
    };

    this.hideContent = this.hideContent.bind(this);
    this.showContent = this.showContent.bind(this);
    this.removeAssessmentComponent = this.removeAssessmentComponent.bind(this);
    this.getResults = this.getResults.bind(this);
  };

  async componentDidMount() {

  };
  componentDidUpdate(prevProps, prevState) {
    if (this.state.showAndHideQuestions) {
      this.newArray(answerStatusObject.answerStatus).forEach(function (element, index) {
        if (index <= 4) {
          let input = document.getElementById(element.id)
          if (input !== null && input.type === "radio") {
            input.checked = true;
          }
          if (input !== null && input.type === "text") {
            input.value = element.answerSelected;
          }
        }
      });
    } else if (!this.state.showAndHideQuestions) {
      this.newArray(answerStatusObject.answerStatus).forEach(function (element, index) {
        if (index > 4) {
          let input = document.getElementById(element.id)
          if (input !== null && input.type === "radio") {
            input.checked = true;
          }
          if (input !== null && input.type === "text") {
            input.value = element.answerSelected;
          }
        }
      });
    }
  };

  newArray(data) {
    let array = [];
    array.push(data.question1)
    array.push(data.question2)
    array.push(data.question3)
    array.push(data.question4)
    array.push(data.question5)
    array.push(data.question6)
    array.push(data.question7)
    array.push(data.question8)
    array.push(data.question9)
    return array;
  }

  propsSlider = () => ({
    name: 'slider',
    inputType: 'number',
    min: 0,
    max: 10,
    value: 1,
    labelText: 'Confidence level',
    step: 1,
  });

  hideContent() {
    let isSecondPartAllowed = true;
    let lessValueRequired = 1;
    let allValuesArray = [];
    for (var i = 1; i <= 5; i++) {
      if (!answerStatusObject.answerStatus['question' + i].answered) {
        isSecondPartAllowed = false;
        allValuesArray.push(i);
        if (document.getElementById("question" + i)) {
          document.getElementById("question" + i).classList.add("required");
        }
      }
    }
    lessValueRequired = Math.min.apply(null, allValuesArray);
    if (isSecondPartAllowed) {
      this.props.getChangeAssessmentPages(false);
      setTimeout(function () {
        if (navigator.userAgent.match(/iPad/i)) {
          window.location.href = "#ibm-top";
        } else {
          window.location.href = "#ibm-top";
        }
      }, 100);
    }
    else {
      window.location.href = "#question_content_question_" + lessValueRequired;
    }
  }

  showContent() {
    this.props.getChangeAssessmentPages(true);
  }

  removeAssessmentComponent() {
    this.props.getRemoveAndShowComponents(false);

  }

  getResults() {
    let isResultComponentAllowed = true;
    let lessValueRequired = 6;
    let allValuesArray = [];
    for (var i = 1; i <= 9; i++) {
      console.log(answerStatusObject.answerStatus['question' + i].answered);
      if (!answerStatusObject.answerStatus['question' + i].answered) {
        isResultComponentAllowed = false;
        allValuesArray.push(i);
      }
    }
    lessValueRequired = Math.min.apply(null, allValuesArray);
    if (isResultComponentAllowed) {
      this.props.getRemoveAndShowResultsComponents(true);
      setTimeout(function () {
        window.location.href = "#ibm-top";
      }, 50);
    }
    else {
      window.location.href = "#question_content_question_" + lessValueRequired;
    }
  }

  render() {
    const {
      additionalProps,
      TextInputPropsQuestion1,
      TextInputPropsQuestion2,
      TextInputPropsQuestion3,
      fieldsetRadioProps,
      radioProps,
      question4ContentRadioButton,
      question5ContentRadioButton,
      showAndHideQuestions,
      question6ContentRadioButton,
      question7ContentRadioButton,
      question8ContentRadioButton,
    } = this.state;

    const {
      assesmentResults,
      removeAndShowResults,
      changeAssessmentPages
    } = this.props;

    return (
      <Page
        removeAndShowResults={removeAndShowResults}
        assesmentResults={assesmentResults}
        additionalProps={additionalProps}
        TextInputPropsQuestion1={TextInputPropsQuestion1}
        TextInputPropsQuestion2={TextInputPropsQuestion2}
        TextInputPropsQuestion3={TextInputPropsQuestion3}
        fieldsetRadioProps={fieldsetRadioProps}
        radioProps={radioProps}
        propsSlider={this.propsSlider}
        question4ContentRadioButton={question4ContentRadioButton}
        question5ContentRadioButton={question5ContentRadioButton}
        showAndHideQuestions={showAndHideQuestions}
        hideContent={this.hideContent}
        showContent={this.showContent}
        question6ContentRadioButton={question6ContentRadioButton}
        question7ContentRadioButton={question7ContentRadioButton}
        question8ContentRadioButton={question8ContentRadioButton}
        removeAssessmentComponent={this.removeAssessmentComponent}
        validateAssessmentData={this.validateAssessmentData}
        getResults={this.getResults}
        changeAssessmentPages={changeAssessmentPages}
      />
    );
  };
};

const mapStateToProps = state => ({
  assesmentResults: state.assesmentResults,
  removeAndShowResults: state.removeAndShowResults,
  allParameter: state.allParameter,
  changeAssessmentPages: state.changeAssessmentPages
});

const mapDispatchToProps = {
  getRemoveAndShowComponents,
  getAssesmentResults,
  getRemoveAndShowResultsComponents,
  getChangeAssessmentPages
};

export default withRouter(
  connect(mapStateToProps, mapDispatchToProps)(AssessmentComponent)
);


