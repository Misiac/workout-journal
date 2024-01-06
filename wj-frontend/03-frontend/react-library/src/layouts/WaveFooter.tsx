import Wave from "react-wavify";
import React from "react";

export const WaveFooter = () => {

    return (
        <div className="wave-footer-container">
            <Wave fill="url(#gradient)" options={{height: 50, speed:0.5}}>
                <defs>
                    <linearGradient id="gradient" gradientTransform="rotate(90)">
                        <stop offset="10%" stopColor="#288dff"/>
                        <stop offset="90%" stopColor="#288dff"/>
                    </linearGradient>
                </defs>
            </Wave>
            <div className="colored-div"></div>
        </div>
    );
}
export default WaveFooter;