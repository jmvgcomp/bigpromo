export default function(...steps){
  const stepByStep = index => {
    if (steps && index < steps.length) {
      
      const /* Callback */ callback = () => stepByStep(index + 1)

      tryExecute(() => steps[index](callback))
        .then(() => {})
        .catch(() => {})
        .finally(callback) 
    }
  }

  stepByStep(0)
}

export function tryExecute(fn) {
  try {
    fn()
    return Promise.resolve()
  } catch(e) {
    return Promise.reject()
  }
}
