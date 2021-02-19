// ==UserScript==
// @name         Chronos Auto Nav
// @namespace    http://tampermonkey.net/
// @version      1.0
// @description  try to take over the world!
// @author       You
// @match        https://chronos.epita.net/ade/standard/gui/tree.jsp*
// @grant        none
// ==/UserScript==

(function() {
    'use strict';

    const search = "TANENBAUM D3";

    const root = document.getElementsByClassName("treeinput")[0];
    const input = root.getElementsByTagName("input")[0];
    const button = root.getElementsByTagName("a")[0];

    if(input.value !== "")
        return;

    input.value = search;
    button.click();

})();