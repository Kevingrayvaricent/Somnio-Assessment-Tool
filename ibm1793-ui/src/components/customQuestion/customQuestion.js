import React, { Component } from 'react';
import { connect } from 'react-redux';
import { withRouter } from 'react-router-dom';
import Page from './customQuestionPage';
import getAssesmentResults from '../../redux/actions/assessmentAction';


class CustomQuestion extends Component {
  constructor(props) {
    super(props);
    this.state = {

    };
    this.inputChange = this.inputChange.bind(this);
  };



  async componentDidMount() {
  };

  componentDidUpdate(prevProps, prevState) {
  };

  inputChange(e, value, event) {
    if (value !== undefined) {
      let elementId = value.replace('question', '');
      this.props.getAssesmentResults({
        question: elementId,
        points: 1,
        answerSelected: e,
        answered: true,
        id: event.target.id
      });
    }
    else {
      let elementId = e.target.id.replace('question', '');
      let elementValue = e.target.value;
      if (document.getElementById("question" + elementId)) {
        document.getElementById("question" + elementId).classList.remove("required");
      }

      if (elementValue !== "") {
        this.props.getAssesmentResults({
          question: elementId,
          points: 1,
          answerSelected: elementValue,
          answered: true,
          id: e.target.id
        });
      }
      else {
        this.props.getAssesmentResults({
          question: elementId,
          points: 1,
          answerSelected: elementValue,
          answered: false,
          id: e.target.id
        });
      }
    }

    this.props.getAssesmentResults({
      question: '9',
      points: 1,
      answered: true,
      answerSelected: '9'
    });
  }

  render() {
    /*
    const {
      
    } = this.state;
    */

    const {
      questionNumber,
      questionText,
      progressBar,
      image,
      name,
      additionalProps,
      textInput,
      isRadioButton,
      fieldsetRadioProps,
      radioProps,
      radioButtonData,
      isSlider,
      propsSlider
    } = this.props;

    return (
      <Page
        questionNumber={questionNumber || ""}
        questionText={questionText || ""}
        progressBar={progressBar || ""}
        image={image || ""}
        name={name || ""}
        additionalProps={additionalProps || ""}
        textInput={textInput || ""}
        isRadioButton={isRadioButton || false}
        fieldsetRadioProps={fieldsetRadioProps || ""}
        radioProps={radioProps || ""}
        radioButtonData={radioButtonData || []}
        isSlider={isSlider || false}
        propsSlider={propsSlider || null}
        inputChange={this.inputChange}
      />
    );
  };
};


const mapStateToProps = state => ({
  assesmentResults: state.assesmentResults,
  allParameter: state.allParameter
});

const mapDispatchToProps = {
  getAssesmentResults
};

export default withRouter(
  connect(mapStateToProps, mapDispatchToProps)(CustomQuestion)
);

