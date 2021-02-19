// ==UserScript==
// @name         CRI auto login
// @namespace    http://tampermonkey.net/
// @version      1.0
// @description  try to take over the world!
// @author       You
// @match        https://moodle.cri.epita.fr/login/index.php
// @grant        none
// ==/UserScript==

(function() {
    'use strict';

    const buttons = document.getElementsByTagName("a");
    for(const b of buttons)
        if(b.title === "CRI")
            b.click();
})();