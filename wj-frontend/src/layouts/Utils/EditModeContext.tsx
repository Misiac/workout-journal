import React from 'react';

    interface EditModeContextTypes {
  isEditModeOn: boolean;
  setIsEditModeOn: React.Dispatch<React.SetStateAction<boolean>>;
}

export const EditModeContext = React.createContext<EditModeContextTypes | null>(null);