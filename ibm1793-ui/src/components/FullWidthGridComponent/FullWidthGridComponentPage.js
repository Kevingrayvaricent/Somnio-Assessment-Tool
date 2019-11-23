import React from 'react';
import { ArrowRight16 } from '@carbon/icons-react';

import questionSvg from '../../assets/images/question.svg';
import answerSvg from '../../assets/images/answer.svg';
import reportSvg from '../../assets/images/report.svg';

import './FullWidthGridComponent.css';

function FullWidthGridComponentPage(props) {
  const { removeResultsComponent } = props;
  return (
    <div className="ibm-fluid ibm-seamless ibm-full-width-grid ibm-fullwidth ibm-flex ibm-flex--wrap">
      <div className="ibm-col-12-3 ibm-full-width-grid-a bright-blue-background bm-padding-content ibm-col-medium-12-6">
        <h3 className="ibm-h3 ibm-padding-content">A modernized SPM solution helps reduce overpayment, optimize compensation and boost sales. Which solution is right for your business?</h3>
        <a href onClick={removeResultsComponent} className="ibm-padding-content ibm-blocklink">Start assessment<ArrowRight16 aria-label="Arrow right" className="arrow-rigth" /></a>
      </div>
      <div className="ibm-col-12-3 black-ligther-background bm-padding-content ibm-col-medium-12-6 ibm-flex">
        <div className="bm-padding-content">
          <img alt="" src={questionSvg} width="70" />
          <h4 className="ibm-h4">Questions</h4>
          <p >Complete a short survey about your organization's SPM experience.</p>
        </div>
      </div>
      <div className="ibm-col-12-3 greyish-brown-background ibm-col-medium-12-6">
        <div className="bm-padding-content">
          <img alt="" src={answerSvg} width="69" />
          <h4 className="ibm-h4">Answers</h4>
          <p>We'll calculate your responses and assemble the results.</p>
        </div>
      </div>
      <div className="ibm-col-12-3 greyish-brown-two-background ibm-col-medium-12-6">
        <div className="bm-padding-content">
          <img alt="" src={reportSvg} width="69" />
          <h4 className="ibm-h4">Report</h4>
          <p>Download an in-depth report with tailored recommendations.</p>
        </div>
      </div>
    </div >
  );
}

export default FullWidthGridComponentPage;
