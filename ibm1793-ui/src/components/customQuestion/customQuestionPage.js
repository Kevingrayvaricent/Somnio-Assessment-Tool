import React, { Fragment } from 'react';
import './customQuestion.scss';
import { TextInput, FormGroup, RadioButtonGroup, RadioButton, Slider } from 'carbon-components-react';

function Page(props) {
  const {
    questionNumber,
    questionText,
    progressBar,
    image,
    name,
    textInput,
    isRadioButton,
    fieldsetRadioProps,
    radioProps,
    radioButtonData,
    isSlider,
    propsSlider,
    inputChange
  } = props;

  return (
    <Fragment>
      <div id={'question_content_' + questionNumber.toLowerCase().replace(' ', '_')} className="ibm-col-1-1">
        <p className="assessment-label">{questionNumber}</p>
        <div className="progress-container">
          <div className={"progress-per-question " + progressBar}></div>
        </div>
        <img alt="" src={image} width="86" />
        <div className="ibm-columns">
          <div className="ibm-col-12-7 ibm-col-medium-12-12">
            <h2 className="ibm-h2 ibm-light ng-binding">{questionText}</h2>
          </div>
        </div>
        {isSlider ?
          <Slider tabIndex="1" id="slider" {...propsSlider()} />
          : isRadioButton ?
            <div>
              <FormGroup {...fieldsetRadioProps} onChange={inputChange}>
                <RadioButtonGroup
                  name={name}
                  onChange={inputChange}>
                  {radioButtonData.map(function (e) {
                    return <RadioButton tabIndex="1" value={e.value} id={e.id} labelText={e.labelText} {...radioProps} />
                  })}
                </RadioButtonGroup>
              </FormGroup>
            </div>
            :
            <div className="ibm-columns">
              <div className="ibm-col-6-2">
                <TextInput tabIndex="1" required {...textInput} onChange={inputChange} />
              </div>
            </div>}
      </div>
      <div className="ibm-rule ibm-alternate"><hr /></div>
    </Fragment >
  );
}

export default Page;