import React from 'react';

import './NavigationMenuComponent.css';

function NavigationMenuComponent(props) {
  const { removeAndShowContent, getResults, removeAssessmentComponent, showAssessmentInitialSection, allowLockResults, showAssessmentFinalSection, removeAndShowResults } = props;
  return (
    <div>
      <nav role="navigation">
        <div className="ibm-sitenav-menu-container">
          <div className="ibm-sitenav-menu-name"></div>
          <div className="ibm-sitenav-menu-list">
            <ul role="menubar">
              <li role="presentation"><a onClick={removeAssessmentComponent} role="menuitem" href data-altlabel="Get Started">Get Started</a></li>
              <li role="presentation">
                <a role="menuitem" href data-altlabel="Assessment">
                  {!removeAndShowContent ? <span className="ibm-secure-link ibm-icon-nolink">Assessment</span> : null}
                  {removeAndShowContent ? <span className="">Assessment</span> : null}
                </a>
                {removeAndShowContent ?
                  <ul role="menu" aria-label="Case studies">
                    <li role="presentation"><a onClick={showAssessmentInitialSection} role="menuitem" href>Questions 1 - 5</a></li>
                    <li role="presentation"><a onClick={showAssessmentFinalSection} role="menuitem" href>Questions 6 - 10</a></li>
                  </ul> : null}
              </li>
              <li role="presentation">

                <a onClick={getResults} role="menuitem" href data-altlabel="Results">
                  {!removeAndShowResults ? !allowLockResults ? <span className="ibm-secure-link ibm-icon-nolink">Results</span> : null : null}
                  {removeAndShowResults ? !allowLockResults ? < span className="">Results</span> : null : null}
                  {allowLockResults ? <span className="">Results</span> : null}
                </a>

              </li>
            </ul>
          </div>
        </div>
      </nav>
    </div >
  );
}

export default NavigationMenuComponent;
