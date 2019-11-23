import React from 'react';

import './StickyHeader.css';

function StickyHeader() {
  return (
    <div id="ibm-masthead" role="banner" aria-label="IBM">
      <div id="ibm-mast-options">
        <ul role="toolbar" aria-labelledby="ibm-masthead">
          <li id="ibm-geo" role="presentation">
            <a href="http://www.ibm.com/planetwide/select/selector.html">United States</a></li>
        </ul>


      </div>
      <div id="ibm-universal-nav">

        <div id="ibm-home"><a href="http://www.ibm.com/us-en/">IBM®</a></div>
        <ul id="ibm-menu-links" role="toolbar" aria-label="Site map">
          <li><a href="http://www.ibm.com/sitemap/us/en/">Site map</a></li>
        </ul>

        <div id="ibm-search-module" role="search" aria-labelledby="ibm-masthead">
          <form id="ibm-search-form" action="http://www.ibm.com/Search/" method="get">
            <p>
              <label htmlFor="q"><span className="ibm-access">Search</span></label>
              <input type="text" maxLength="100" placeholder="Search" name="q" id="q" />
              <input type="hidden" value="17" name="v" />
              <input type="hidden" value="utf" name="en" />
              <input type="hidden" value="en" name="lang" />
              <input type="hidden" value="us" name="cc" />
              <input type="submit" id="ibm-search" className="ibm-btn-search" value="Submit" />
            </p>
          </form>
        </div>
      </div>
    </div>
  );
}

export default StickyHeader;
