import React from "react";

// 주현우
export function replaceNewlinesWithBr(content) {
  if (typeof content !== "string") {
    return content;
  }

  const actualNewlines = content.replace(/\\n/g, "\n").replace(/\\r/g, "\r");

  return actualNewlines.split(/\r?\n/).map((line, index) => (
    <React.Fragment key={index}>
      {line}
      <br />
    </React.Fragment>
  ));
}

export function replaceNewlinesWithSpace(content) {
  if (typeof content !== "string") {
    return content;
  }

  return content.replace(/\\n/g, " ");
}
