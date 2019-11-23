import React, { Fragment } from 'react';
import './AssessmentComponent.scss';
import { ArrowRight16 } from '@carbon/icons-react';
import globalFinanceSvg from '../../assets/images/global-finance.svg';
import financeGraphSvg from '../../assets/images/finance-graph.svg';
import financePersonSvg from '../../assets/images/finance-person.svg';
import financeGroupSvg from '../../assets/images/finance-group.svg';
import financeSalesSvg from '../../assets/images/finance-sales.svg';
import financeChatSvg from '../../assets/images/finance-chat.svg';
import financePaySvg from '../../assets/images/finance-pay.svg';
import financeTimeSvg from '../../assets/images/finance-time.svg';
import financeConfidenceSvg from '../../assets/images/finance-confidence.svg';
import CustomQuestion from '../customQuestion/customQuestion';
import { Form } from 'carbon-components-react';

function Page(props) {
  const {
    removeAndShowContent,
    removeAndShowResults,
    additionalProps,
    TextInputPropsQuestion1,
    TextInputPropsQuestion2,
    TextInputPropsQuestion3,
    fieldsetRadioProps,
    radioProps,
    propsSlider,
    question4ContentRadioButton,
    question5ContentRadioButton,
    hideContent,
    showContent,
    question6ContentRadioButton,
    question7ContentRadioButton,
    question8ContentRadioButton,
    changeAssessmentPages,
    getResults
  } = props;
  return (
    <Fragment>
      {!removeAndShowContent ? !removeAndShowResults ?
        <div id="ibm-content-body">
          <Form {...additionalProps}>
            <div id="assessment" className="assessment">
              <div className="ibm-columns">
                {changeAssessmentPages ?
                  <div className="ibm-assessment-part-1">
                    <CustomQuestion
                      questionNumber={"Question 1"}
                      questionText={"What was your organization's revenue for the last fiscal year?"}
                      progressBar={"progress-per-question-1-10"}
                      image={globalFinanceSvg}
                      additionalProps={additionalProps}
                      textInput={TextInputPropsQuestion1}
                      tabIndex={1}
                    />
                    <CustomQuestion
                      questionNumber={"Question 2"}
                      questionText={"Gartner estimates that organizations overpay sales representatives by 3%-5%. Estimate how much you are overpaying sales reps."}
                      progressBar={"progress-per-question-2-10"}
                      image={financeGraphSvg}
                      additionalProps={additionalProps}
                      textInput={TextInputPropsQuestion2}
                    />
                    <CustomQuestion
                      questionNumber={"Question 3"}
                      questionText={"How many sales reps receive incentive compensation, like commissions or bonuses, in your organization?"}
                      progressBar={"progress-per-question-3-10"}
                      image={financePersonSvg}
                      additionalProps={additionalProps}
                      textInput={TextInputPropsQuestion3}
                    />
                    <CustomQuestion
                      questionNumber={"Question 4"}
                      questionText={"Do you anticipate hiring additional sales reps in the next three years?"}
                      progressBar={"progress-per-question-4-10"}
                      image={financeGroupSvg}
                      isRadioButton={true}
                      name="question4"
                      fieldsetRadioProps={fieldsetRadioProps}
                      radioProps={radioProps}
                      radioButtonData={question4ContentRadioButton}
                    />
                    <CustomQuestion
                      questionNumber={"Question 5"}
                      questionText={"How are you currently managing your sales incentive compensation? Select one:"}
                      progressBar={"progress-per-question-5-10"}
                      image={financeSalesSvg}
                      isRadioButton={true}
                      name="question5"
                      fieldsetRadioProps={fieldsetRadioProps}
                      radioProps={radioProps}
                      radioButtonData={question5ContentRadioButton}
                    />
                    <p className="ibm-button-link ibm-alternate-background">
                      <a className="ibm-btn-pri" href onClick={() => hideContent()}>Next
                  <ArrowRight16 aria-label="Arrow right" className="arrow-rigth" /></a>
                    </p>
                  </div>
                  :
                  <div >
                    <p className="ibm-button-link ibm-alternate-background">
                      <a className="ibm-previus-link" href onClick={() => showContent()}><span>←</span> Previous</a>
                    </p>
                    <div className="ibm-rule ibm-alternate full-size"><hr /></div>
                    <div className="ibm-assessment-part-2">
                      <CustomQuestion
                        questionNumber={"Question 6"}
                        questionText={"How many full-time employees manage your current sales incentive compensation? Select one:"}
                        progressBar={"progress-per-question-6-10"}
                        image={financeChatSvg}
                        isRadioButton={true}
                        name="question6"
                        fieldsetRadioProps={fieldsetRadioProps}
                        radioProps={radioProps}
                        radioButtonData={question6ContentRadioButton}
                      />

                      <CustomQuestion
                        questionNumber={"Question 7"}
                        questionText={"How often do you pay out sales incentive compensation to the sales team? Select one:"}
                        progressBar={"progress-per-question-7-10"}
                        image={financePaySvg}
                        isRadioButton={true}
                        name="question7"
                        fieldsetRadioProps={fieldsetRadioProps}
                        radioProps={radioProps}
                        radioButtonData={question7ContentRadioButton}
                      />

                      <CustomQuestion
                        questionNumber={"Question 8"}
                        questionText={"How many hours are spent handling inquiries from the sales team per payout period?"}
                        progressBar={"progress-per-question-8-10"}
                        image={financeTimeSvg}
                        isRadioButton={true}
                        fieldsetRadioProps={fieldsetRadioProps}
                        radioProps={radioProps}
                        name="question8"
                        radioButtonData={question8ContentRadioButton}
                      />

                      <CustomQuestion
                        questionNumber={"Question 9"}
                        questionText={"On a scale of 1-10, how confident are you in your ability to access your sales data to make informed decisions?"}
                        progressBar={"progress-per-question-9-10"}
                        image={financeConfidenceSvg}
                        isSlider={true}
                        propsSlider={propsSlider}
                      />

                      <p className="ibm-button-link ibm-alternate-background">
                        <a onClick={getResults} href className="ibm-btn-pri submit">Submit
                     <ArrowRight16 aria-label="Arrow right" className="arrow-rigth" /></a>
                      </p>
                    </div>
                  </div>
                }
              </div>
            </div>
          </Form>
        </div >
        : null : null}
    </Fragment >
  );
}

export default Page;