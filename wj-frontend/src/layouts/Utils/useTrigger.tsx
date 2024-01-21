import {useState} from "react";
const useTrigger = () => {

    class StateHolder {
        private state: boolean;
        private setState: any;

        constructor() {
            const [variable, setVariable] = useState(false);
            this.state = variable;
            this.setState = setVariable;
        }

        trigger(): void {
            this.setState(!this.state);
        }
    }

    return new StateHolder();
}

export default useTrigger;