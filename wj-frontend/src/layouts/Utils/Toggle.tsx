import React from 'react';

interface EditModeToggleProps {
    isEditModeOn: boolean;
    handleOpenModal: () => void;
}

class EditModeToggle extends React.Component<EditModeToggleProps> {
    render() {
        const { isEditModeOn, handleOpenModal } = this.props;

        return (
            <label className='flex cursor-pointer select-none items-center py-6'>
                <div className='relative'>
                    <input
                        type='checkbox'
                        checked={isEditModeOn}
                        onChange={handleOpenModal}
                        className='sr-only'
                    />
                    <div
                        className={`box block h-8 w-14 rounded-full ${
                            isEditModeOn ? 'bg-regal-blue' : 'bg-black'
                        }`}
                    ></div>
                    <div
                        className={`absolute left-1 top-1 flex h-6 w-6 items-center justify-center rounded-full bg-white transition ${
                            isEditModeOn ? 'translate-x-full' : ''
                        }`}
                    ></div>
                </div>
                <span className="px-3 text-sm font-bold">EDIT MODE</span>
            </label>
        );
    }
}

export default EditModeToggle;