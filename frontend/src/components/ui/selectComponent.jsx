import React from "react";
import Select from "react-select";

const SelectComponent = ({ label, options, defaultValue, isMulti, placeholder, width, value, onChange, isDisabled}) => {
  const customStyles = {
    container: (provided) => ({
      ...provided,
      width: width || "100%", 
      minWidth: "270px",
      fontSize: "14px",
    }),
    placeholder: (provided) => ({
      ...provided,
      color: "#ccc",
    }),
    
  };

  return (
    <div style={{ marginBottom: "20px" }}>
      <label style={{ display: "block", marginBottom: "8px", fontWeight: "bold" }}>
        {label}
      </label>
      <Select
        options={options}
        value={
         isMulti
          ? options.filter((option) => Array.isArray(value) && value.includes(option.value))
          : options.find((option) => option.value === value)
        }
        onChange={onChange}
        defaultValue={defaultValue}
        isMulti={isMulti}
        placeholder={placeholder}
        styles={customStyles}
        isDisabled={isDisabled}
      />
    </div>
  );
};

export default SelectComponent;
